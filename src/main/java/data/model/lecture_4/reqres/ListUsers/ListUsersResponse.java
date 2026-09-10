package data.model.lecture_4.reqres.ListUsers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListUsersResponse{

	@JsonProperty("per_page")
	private Integer perPage;

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("data")
	private List<DataItem> data;

	@JsonProperty("_meta")
	private Meta meta;

	@JsonProperty("page")
	private Integer page;

	@JsonProperty("total_pages")
	private Integer totalPages;

	@JsonProperty("support")
	private Support support;

	public void setPerPage(Integer perPage){
		this.perPage = perPage;
	}

	public Integer getPerPage(){
		return perPage;
	}

	public void setTotal(Integer total){
		this.total = total;
	}

	public Integer getTotal(){
		return total;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setMeta(Meta meta){
		this.meta = meta;
	}

	public Meta getMeta(){
		return meta;
	}

	public void setPage(Integer page){
		this.page = page;
	}

	public Integer getPage(){
		return page;
	}

	public void setTotalPages(Integer totalPages){
		this.totalPages = totalPages;
	}

	public Integer getTotalPages(){
		return totalPages;
	}

	public void setSupport(Support support){
		this.support = support;
	}

	public Support getSupport(){
		return support;
	}

	@Override
 	public String toString(){
		return 
			"ListUsersResponse{" + 
			"per_page = '" + perPage + '\'' + 
			",total = '" + total + '\'' + 
			",data = '" + data + '\'' + 
			",_meta = '" + meta + '\'' + 
			",page = '" + page + '\'' + 
			",total_pages = '" + totalPages + '\'' + 
			",support = '" + support + '\'' + 
			"}";
		}
}