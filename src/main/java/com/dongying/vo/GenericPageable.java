package com.dongying.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class GenericPageable {

	private int currentPageNo;

	private int pageDataSize;

	private int dataTotalSize;

	private int[] pagination;

	private int endPageNo;

}
