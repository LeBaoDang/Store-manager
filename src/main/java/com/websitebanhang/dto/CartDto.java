package com.websitebanhang.dto;

import java.io.Serializable;
import java.util.HashMap;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDto implements Serializable {

	private static final long serialVersionUID = -5485130851677997194L;

	private Long orderId;
	private String address;
	private String phone;
	private Double totalPrice = 0D;
	private Integer totalQuantity = 0;
	private HashMap<Long, CartDetailDto> details = new HashMap<>();
	
}
