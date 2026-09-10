package data.model.lecture_4.reqres.ListUsers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta{

	@JsonProperty("docs_url")
	private String docsUrl;

	@JsonProperty("cta")
	private Cta cta;

	@JsonProperty("powered_by")
	private String poweredBy;

	@JsonProperty("variant")
	private String variant;

	@JsonProperty("context")
	private String context;

	@JsonProperty("message")
	private String message;

	@JsonProperty("example_url")
	private String exampleUrl;

	@JsonProperty("upgrade_url")
	private String upgradeUrl;

	public void setDocsUrl(String docsUrl){
		this.docsUrl = docsUrl;
	}

	public String getDocsUrl(){
		return docsUrl;
	}

	public void setCta(Cta cta){
		this.cta = cta;
	}

	public Cta getCta(){
		return cta;
	}

	public void setPoweredBy(String poweredBy){
		this.poweredBy = poweredBy;
	}

	public String getPoweredBy(){
		return poweredBy;
	}

	public void setVariant(String variant){
		this.variant = variant;
	}

	public String getVariant(){
		return variant;
	}

	public void setContext(String context){
		this.context = context;
	}

	public String getContext(){
		return context;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setExampleUrl(String exampleUrl){
		this.exampleUrl = exampleUrl;
	}

	public String getExampleUrl(){
		return exampleUrl;
	}

	public void setUpgradeUrl(String upgradeUrl){
		this.upgradeUrl = upgradeUrl;
	}

	public String getUpgradeUrl(){
		return upgradeUrl;
	}

	@Override
 	public String toString(){
		return 
			"Meta{" + 
			"docs_url = '" + docsUrl + '\'' + 
			",cta = '" + cta + '\'' + 
			",powered_by = '" + poweredBy + '\'' + 
			",variant = '" + variant + '\'' + 
			",context = '" + context + '\'' + 
			",message = '" + message + '\'' + 
			",example_url = '" + exampleUrl + '\'' + 
			",upgrade_url = '" + upgradeUrl + '\'' + 
			"}";
		}
}