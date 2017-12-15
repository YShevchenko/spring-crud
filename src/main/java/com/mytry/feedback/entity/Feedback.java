package com.mytry.feedback.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min=3, max=30)
    private String author;

    @NotNull
    @Size(max=200)
    private String summary;
    @Column(length = 2000)
    @Size(max = 2000)
    private String content;

    @NotNull
    @Size(min=3, max=30)
    private String subject;
    private Boolean recommend;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id) &&
                Objects.equals(author, feedback.author) &&
                Objects.equals(summary, feedback.summary) &&
                Objects.equals(content, feedback.content) &&
                Objects.equals(subject, feedback.subject) &&
                Objects.equals(recommend, feedback.recommend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, summary, content, subject, recommend);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", subject='" + subject + '\'' +
                ", recommend=" + recommend +
                '}';
    }
}
