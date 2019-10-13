package com.number47.learn.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author number47
 * @date 2019/10/14 02:10
 * @description
 */
@Data
public class MyUser implements Serializable {

	private static final long serialVersionUID = 3497935890426858541L;

	private String userName;

	private String password;

	private boolean accountNonExpired = true;

	private boolean accountNonLocked= true;

	private boolean credentialsNonExpired= true;

	private boolean enabled= true;

}
