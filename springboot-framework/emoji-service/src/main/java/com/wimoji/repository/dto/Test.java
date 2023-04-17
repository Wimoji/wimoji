package com.wimoji.repository.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(
        collection = "testdb"
)
public class Test {
    @Id
    private String _id;
    private String title;
    private String content;

    public Test() {
    }

    public String get_id() {
        return this._id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public void set_id(final String _id) {
        this._id = _id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Test)) {
            return false;
        } else {
            Test other = (Test)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$_id = this.get_id();
                    Object other$_id = other.get_id();
                    if (this$_id == null) {
                        if (other$_id == null) {
                            break label47;
                        }
                    } else if (this$_id.equals(other$_id)) {
                        break label47;
                    }

                    return false;
                }

                Object this$title = this.getTitle();
                Object other$title = other.getTitle();
                if (this$title == null) {
                    if (other$title != null) {
                        return false;
                    }
                } else if (!this$title.equals(other$title)) {
                    return false;
                }

                Object this$content = this.getContent();
                Object other$content = other.getContent();
                if (this$content == null) {
                    if (other$content != null) {
                        return false;
                    }
                } else if (!this$content.equals(other$content)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Test;
    }

    public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $_id = this.get_id();
        result = result * 59 + ($_id == null ? 43 : $_id.hashCode());
        Object $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        Object $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        return result;
    }

    public String toString() {
        String var10000 = this.get_id();
        return "Test(_id=" + var10000 + ", title=" + this.getTitle() + ", content=" + this.getContent() + ")";
    }
}
