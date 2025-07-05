package ar.ziphra.common.model;
import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement  
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Placeholder implements Serializable {

  private static final long serialVersionUID = 1L;

@XmlValue
  public String string ;


@XmlAttribute  	
  public String name ;
  
  //ivate String value;
}