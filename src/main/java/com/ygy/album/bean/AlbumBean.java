package com.ygy.album.bean;

import java.util.List;

public class AlbumBean {
    private String nick;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String title;
    private String album_info;
    private String label;
    private String createtime;
    private List<PictureBean> list;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum_info() {
        return album_info;
    }

    public void setAlbum_info(String album_info) {
        this.album_info = album_info;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public List<PictureBean> getList() {
        return list;
    }

    public void setList(List<PictureBean> list) {
        this.list = list;
    }
}
