package com.dongying.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
public class GenericPageable {

	private int currentPageNo;

	private int pageDataSize;

	private int totalPage;

	private int dataTotalSize;

	private List<Integer> pagination;

	public List<Integer> getPagination() {
		return pagination;
	}

	public void setPagination(int totalPage) {
		List<Integer> pagination = new ArrayList<>();
		int startPageNo = 1;
		int endPageNo = totalPage;
		int visiblePages = 5;
		int halfVisiblePages = visiblePages / 2;

		if (totalPage <= visiblePages) {
			for (int i = 1; i <= totalPage; i++) {
				pagination.add(i);
			}
		} else {
			if (currentPageNo <= halfVisiblePages) {
				endPageNo = visiblePages;
			} else if (currentPageNo >= (totalPage - halfVisiblePages)) {
				startPageNo = totalPage - visiblePages + 1;
			} else {
				startPageNo = currentPageNo - halfVisiblePages;
				endPageNo = currentPageNo + halfVisiblePages;
			}

			for (int i = startPageNo; i <= endPageNo; i++) {
				pagination.add(i);
			}
		}

		this.pagination = pagination;
	}

}
