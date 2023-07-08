package com.dongying.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class GoodsVo {

	private long goodsID;

	@NotNull
	@NotBlank
	private String goodsName;

	private String description;

	@Min(value = 0)
	private int price;

	@Min(value = 0)
	private int quantity;
	
	@Min(value = 0)
	private int buyQuantity;

	private MultipartFile file;

	@NotNull
	@NotBlank
	private String imageName;

	@NotNull
	@NotBlank
	@Pattern(regexp = "^[0,1]$", message = "只能0或1")
	private String status;

}
