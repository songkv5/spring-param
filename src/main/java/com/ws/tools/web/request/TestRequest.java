package com.ws.tools.web.request;

import com.ws.tools.annotations.ValidParam;

import java.io.Serializable;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:58
 */
public class TestRequest implements Serializable{
    private Integer id;
    @ValidParam(notNull = true)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
