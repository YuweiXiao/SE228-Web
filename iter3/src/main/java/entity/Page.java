package entity;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private int pageSize;
    private int totalPage; 
    private int rowCount;
    private int currentPage;
    private int prePage; 
    private int nextPage;
    private boolean hasNextPage = true;
    private boolean hasPreviousPage = true;
    private List list;

    public Page(int pageno, int rows) {
    	currentPage = pageno;
    	pageSize = rows;
    	prePage = pageno - 1;
    	if(prePage == 0)
    		hasPreviousPage = false;
    	nextPage = pageno + 1;
    }
    
    public Page() {
        this.pageSize=10;
    }

    public int getOffset() {
    	return (currentPage-1) * pageSize;
    }
    
    /*getters and setters*/
    
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		setTotalPage((rowCount + pageSize - 1) / pageSize);
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
		if(nextPage > totalPage)
			hasNextPage = false;
		list = new ArrayList();
		for(int i = currentPage - 3; i <= currentPage + 3 && i <= totalPage ; i++) {
			if( i < 1)
				continue;
			list.add(i);
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getRowCount() {
		return rowCount;
	}
	
}