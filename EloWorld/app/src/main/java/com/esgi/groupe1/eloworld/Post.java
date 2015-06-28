package com.esgi.groupe1.eloworld;

/**
 * Created by Christopher on 24/06/2015.
 */
public class Post {
    private String userName;
    private String textPost;

    public Post(String userName, String textPost) {
        this.userName = userName;
        this.textPost = textPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (userName != null ? !userName.equals(post.userName) : post.userName != null)
            return false;
        return !(textPost != null ? !textPost.equals(post.textPost) : post.textPost != null);

    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (textPost != null ? textPost.hashCode() : 0);
        return result;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTextPost() {
        return textPost;
    }

    public void setTextPost(String textPost) {
        this.textPost = textPost;
    }
}
