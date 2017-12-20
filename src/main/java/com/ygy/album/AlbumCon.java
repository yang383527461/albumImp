package com.ygy.album;

import com.ygy.album.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AlbumCon {
    private BaseBean base;
    private List<Map<String,String>> list;

    @Autowired
    private MainRequest request;

    public AlbumCon() {
    }

    @ResponseBody
    @RequestMapping(value = "/getString", method = RequestMethod.GET)
    public String getString(@RequestParam("id") String id){
        return request.getString(id);
    }

    //登录
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public BaseBean login(@RequestParam("username") String username, @RequestParam("password") String password){
        UserBean user;
        try {
             user= request.login(username, password);
        }catch (Exception e){
            return new BaseBean(false,null,null,null);
        }
        if(user != null){
            base = new BaseBean(true,"200",user,"登录成功");
        }else{
            base = new BaseBean(true,"505",user,"登录失败");
        }
        return base;
    }

    //注册
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public BaseBean register(@RequestParam("username") String username,
                             @RequestParam("nick") String nick,
                             @RequestParam("password") String password,
                             @RequestParam("token") String token){
        Boolean isSuccess;
        try {
            isSuccess= request.register(username, nick, password, token);
        }catch (Exception e){
            return new BaseBean(true,"100",null,"用户名已存在");
        }
        if(isSuccess == true){
            base = new BaseBean(true,"200",isSuccess,"注册成功");
        }else{
            base = new BaseBean(true,"505",isSuccess,"注册失败");
        }
        return base;
    }

    //修改密码
    @ResponseBody
    @RequestMapping(value = "/changePwd", method = RequestMethod.GET)
    public BaseBean changePwd(@RequestParam("password") String password,
                             @RequestParam("token") String token){
        Boolean isSuccess;
        try {
            isSuccess= request.changePwd(token,password);
        }catch (Exception e){
            return new BaseBean(false,null,null,null);
        }
        if(isSuccess == true){
            base = new BaseBean(true,"200",isSuccess,"修改成功");
        }else{
            base = new BaseBean(true,"505",isSuccess,"修改失败");
        }
        return base;
    }

    //获取感兴趣标签
    @ResponseBody
    @RequestMapping(value = "/getLove", method = RequestMethod.GET)
    public BaseBean changePwd(@RequestParam("token") String token){
        LoveBean loveBean;
        list = new ArrayList<>();

        try {
            loveBean= request.getUser(token);
        }catch (Exception e){
            return new BaseBean(false,null,null,null);
        }
        String[] str = loveBean.getLove().split(",");
        for(int i=0; i<str.length; i++){
            Map<String, String> map = new HashMap<>();;
            map.put("love",str[i]);
            list.add(map);
        }
        loveBean.setData(list);
        loveBean.setLove("");
        if(loveBean != null){
            base = new BaseBean(true,"200",loveBean,"查找成功");
        }else{
            base = new BaseBean(true,"505",loveBean,"查找失败");
        }
        return base;
    }

    //修改感兴趣标签
    @ResponseBody
    @RequestMapping(value = "/changeLove", method = RequestMethod.GET)
    public BaseBean changeLove(@RequestParam("token") String token,
                              @RequestParam("love") String love){
        Boolean isSuccess;
        try {
            isSuccess= request.changeLove(token, love);
        }catch (Exception e){
            return new BaseBean(false,null,null,null);
        }
        if(isSuccess == true){
            base = new BaseBean(true,"200",isSuccess,"修改成功");
        }else{
            base = new BaseBean(true,"505",isSuccess,"修改失败");
        }
        return base;
    }

    //发现照片
    @ResponseBody
    @RequestMapping(value = "/getPicture", method = RequestMethod.GET)
    public BaseBean getPicture(@RequestParam("token") String token){
        List<AlbumBean> albumBeans;
        List<PictureBean> pictureBeans;
        try {
            albumBeans= request.getAlbum(token);
            for(int i=0; i<albumBeans.size(); i++){
                pictureBeans = request.getPicture(albumBeans.get(i).getId());
                albumBeans.get(i).setList(pictureBeans);
            }
        }catch (Exception e){
            return new BaseBean(false,null,null,null);
        }
        if(albumBeans != null){
            base = new BaseBean(true,"200",albumBeans,"查询成功");
        }else{
            base = new BaseBean(true,"505",albumBeans,"查询失败");
        }
        return base;
    }

    //关注
    @ResponseBody
    @RequestMapping(value = "/follow", method = RequestMethod.GET)
    public BaseBean follow(@RequestParam("token") String token,
                              @RequestParam("userid") String userid){
        Boolean isSuccess;
        try {
            isSuccess= request.follow(token,userid);
        }catch (Exception e){
            return new BaseBean(true,"505",null,"你已关注");
        }
        if(isSuccess == true){
            base = new BaseBean(true,"200",isSuccess,"关注成功");
        }else{
            base = new BaseBean(true,"505",isSuccess,"关注失败");
        }
        return base;
    }


    //获得关注列表
    @ResponseBody
    @RequestMapping(value = "/getFollow", method = RequestMethod.GET)
    public BaseBean getFollow(@RequestParam("token") String token){
        List<Integer> follows;
        UserBean user;
        List<UserBean> users = new ArrayList<>();
        try {
            follows= request.getFollowId(token);
            for(int i=0; i<follows.size(); i++){
                user = request.getFollow(follows.get(i).toString());
                users.add(user);
            }
        }catch (Exception e){
            return new BaseBean(false,"505",null,"");
        }
        if(users != null){
            base = new BaseBean(true,"200",users,"查询成功");
        }else{
            base = new BaseBean(true,"505",users,"查询失败");
        }
        return base;
    }


    //关注
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public BaseBean comment(@RequestParam("token") String token,
                           @RequestParam("albumid") String albumid,
                            @RequestParam("comment") String comment){
        Integer isSuccess;
        try {
            isSuccess = request.comment(token, albumid, comment);
        }catch (Exception e){
            return new BaseBean(true,"505",e.toString(),"");
        }
        if(isSuccess > 0){
            base = new BaseBean(true,"200",isSuccess,"发表成功");
        }else{
            base = new BaseBean(true,"505",isSuccess,"发表失败");
        }
        return base;
    }

    //获取评论
    @ResponseBody
    @RequestMapping(value = "/getComment", method = RequestMethod.GET)
    public BaseBean comment(@RequestParam("albumid") String albumid){
        List<CommentBean> commentBeans;
        try {
            commentBeans = request.getComment(albumid);
        }catch (Exception e){
            return new BaseBean(true,"505",e.toString(),"");
        }
        if(commentBeans != null){
            base = new BaseBean(true,"200",commentBeans,"发表成功");
        }else{
            base = new BaseBean(true,"505",commentBeans,"发表失败");
        }
        return base;
    }



}
