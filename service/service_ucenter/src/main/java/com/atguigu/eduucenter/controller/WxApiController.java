package com.atguigu.eduucenter.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.eduucenter.pojo.UcenterMember;
import com.atguigu.eduucenter.service.UcenterMemberService;
import com.atguigu.eduucenter.utils.ConstantWxUtils;
import com.atguigu.eduucenter.utils.HttpClientUtils;
import com.atguigu.servicebase.exceptionhandler.MyException;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx/")
public class WxApiController {

    @Resource
     private UcenterMemberService ucenterMemberService;

    @GetMapping("callback")
    public String callback(String code, String state) {

            //得到授权临时票据code
            System.out.println(code);
            System.out.println(state);
//从redis中将state获取出来，和当前传入的state作比较
//如果一致则放行，如果不一致则抛出异常：非法访问
//向认证服务器发送请求换取access_token
            String baseAccessTokenUrl =
                    "https://api.weixin.qq.com/sns/oauth2/access_token" +
                            "?appid=%s" +
                            "&secret=%s" +
                            "&code=%s" +
                            "&grant_type=authorization_code";
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code);
            String result = null;
            try {
                result = HttpClientUtils.get(accessTokenUrl);
                System.out.println("accessToken=============" + result);
            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException(20001, "获取access_token失败");
            }

        //解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");




        try {


            //判断数据表里是微信id否已存在
            UcenterMember ucenterMember = ucenterMemberService.getOpenIdMember(openid);
            if(ucenterMember==null){//是空就加
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                //访问微信的资源服务器，获取用户信息
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap userMap = gson.fromJson(userInfo, HashMap.class);
                String  nickname = (String)userMap.get("nickname");
                String headimgurl =(String) userMap.get("headimgurl");
                ucenterMember = new UcenterMember();
                ucenterMember.setOpenid(openid);
                ucenterMember.setNickname(nickname);
                ucenterMember.setAvatar(headimgurl);
                ucenterMemberService.save(ucenterMember);
            }
            String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001,"登录失败");

        }
        //使用jwt根据ucenterMember对象生成token字符串

    }


    //生成二维码
    @GetMapping("wxlogin")
    public String getWxCode() {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String redirect_url = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        String encode = null;
        try {
            encode = URLEncoder.encode(redirect_url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                encode,
                "atguigu"
        );


        return "redirect:" + url;
    }
}
