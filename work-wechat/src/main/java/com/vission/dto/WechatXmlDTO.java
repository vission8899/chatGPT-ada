package com.vission.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * @author vission
 * @date 2023/3/7 8:40 上午
 * @description
 */
@XmlRootElement(name = "xml")
@Data
public class WechatXmlDTO implements Serializable {

    private static final long serialVersionUID = 10002L;

    @XmlElement(name = "ToUserName")
    private String ToUserName;

    @XmlElement(name = "AgentID")
    private String AgentID;

    @XmlElement(name = "Encrypt")
    private String Encrypt;
}
