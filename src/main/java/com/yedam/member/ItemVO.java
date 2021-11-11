package com.yedam.member;

public class ItemVO {
	private int prodId;
	private String prodName;
	private String prodDesc;
	private double likeIt;
	private int originPrice;
	private int salePrice;
	private String prodImage;
	public int getProdId() {
		return prodId;
	}
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdDesc() {
		return prodDesc;
	}
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	public double getLikeIt() {
		return likeIt;
	}
	public void setLikeIt(double likeIt) {
		this.likeIt = likeIt;
	}
	public int getOriginPrice() {
		return originPrice;
	}
	public void setOriginPrice(int originPrice) {
		this.originPrice = originPrice;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	public String getProdImage() {
		return prodImage;
	}
	public void setProdImage(String prodImage) {
		this.prodImage = prodImage;
	}
	@Override
	public String toString() {
		return "ItemVO [prodId=" + prodId + ", prodName=" + prodName + ", prodDesc=" + prodDesc + ", likeIt=" + likeIt
				+ ", originPrice=" + originPrice + ", salePrice=" + salePrice + ", prodImage=" + prodImage + "]";
	}
	
	
	
}

