package com.ygy.album;

import com.ygy.album.bean.*;
import org.apache.ibatis.annotations.*;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Mapper
public interface MainRequest {
    @Select("select nick from user where id=#{id}")
    String getString(@Param("id") String id);

    //登录
    @Select("select * from user where username = #{username} and password = #{password}")
    UserBean login(@Param("username")String username,
                   @Param("password") String password);

    //注册
    @Insert("insert into user(username,nick,password,create_time,token) values(#{username},#{nick},#{password},now(),#{token})")
    Boolean register(@Param("username") String username,
                     @Param("nick") String nick,
                     @Param("password") String password,
                     @Param("token") String token);

    //修改密码
    @Update("update user set password=#{password} where token=#{token}")
    Boolean changePwd(@Param("token") String token,
                      @Param("password") String password);

    //发现栏目
    //@Select("select ")

    //获得用户感兴趣标签
    @Select("select * from user where token=#{token}")
    LoveBean getUser(@Param("token") String token);

    //更改感兴趣标签
    @Update("UPDATE user set love=#{love} where token = #{token}")
    Boolean changeLove(@Param("token") String token,
                       @Param("love") String love);

    //获取图片
    @Select("select * from picture where album_id=#{id}")
    List<PictureBean> getPicture(@Param("id") String id);

    //获取相册
    @Select("select * from album,user where album.user_id=user.id and user.token=#{token}")
    List<AlbumBean> getAlbum(@Param("token") String token);

    //关注
    @Insert("INSERT INTO relation(user_id,user_id_follow) VALUES((SELECT id from user where token = #{token}),#{userid})")
    Boolean follow(@Param("token") String token,
                   @Param("userid") String userid);

    //获取关注人的userid
    @Select("SELECT user_id_follow from relation,user where user.id = relation.user_id and token =#{token}")
    List<Integer> getFollowId(@Param("token") String token);

    //获取关注人信息
    @Select("select * from user where id=#{id}")
    UserBean getFollow(@Param("id") String id);

    //发表评论
    @Insert("INSERT INTO common(user_id,album_id,commont_info,commont_status,create_time) VALUES((select id from user where token = #{token}),#{albumid},#{comment},'0',now())")
    Integer comment(@Param("token") String token,
                    @Param("albumid") String albumid,
                    @Param("comment") String comment);

    //获取评论
    @Select("select nick,commont_info,common.create_time from user,common where user.id = common.user_id and album_id = #{albumid} and commont_status = '0' ORDER BY common.create_time")
    List<CommentBean> getComment(@Param("albumid") String albumid);
}
