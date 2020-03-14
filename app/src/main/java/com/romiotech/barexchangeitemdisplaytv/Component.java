package com.romiotech.barexchangeitemdisplaytv;

public class Component {
	
	String drinkCategoryId = null;
	String drinkCategoryName = null;

	String drinkItemPrice = null;
	String drinkItemLowPrice = null;
	String drinkItemHighPrice = null;

	String drinkItemId = null;
	String drinkItemName = null;
	String drinkItemCost = null;
	String drinkItemTaxSlot = null;
	String drinkItemServiceTax = null;
	String drinkItemUnitName = null;
	String drinkItemColor = null;
	String drinkItemQty = null;
	String drinkItemComment = null;
	String drinkItemTax = null;
	String drinkItemIdDept = null;
	String drinkItemDescription = null;
	String drinkItemDiscountCode = null;
	String drinkItemImage = null;
	String drinkItemOutletId = null;
	String drinkItemIsDeal = null;
	String drinkItemOptional = null;
	String drinkItemIsHappyHour = null;
	String drinkItemHappyHourRate = null;
	String drinkItemMinRate = null;
	String drinkItemMaxRate = null;
	String drinkItemCurrentRate = null;
	String drinkItemLastRate = null;
	String drinkItemstarttime = null;
	String drinkItemcurrenttime = null;


	public Component(){

	}

	public Component(String drinkCategoryId, String drinkCategoryName) {
		super();
		this.drinkCategoryId = drinkCategoryId;
		this.drinkCategoryName = drinkCategoryName;

	}

	public Component(String drinkItemName, String drinkItemLowPrice, String drinkItemHighPrice, String drinkItemPrice) {
		super();
		this.drinkItemName = drinkItemName;
		this.drinkItemLowPrice = drinkItemLowPrice;
		this.drinkItemHighPrice = drinkItemHighPrice;
		this.drinkItemPrice = drinkItemPrice;

	}

	public Component(String drinkCategoryName, String drinkItemName, String drinkItemPrice) {
		super();
		this.drinkCategoryName = drinkCategoryName;
		this.drinkItemName = drinkItemName;
		this.drinkItemPrice = drinkItemPrice;

	}

	public String getDrinkCategoryId() {
		return drinkCategoryId;
	}

	public void setDrinkCategoryId(String drinkCategoryId) {
		this.drinkCategoryId = drinkCategoryId;
	}

	public String getDrinkCategoryName() {
		return drinkCategoryName;
	}

	public void setDrinkCategoryName(String drinkCategoryName) {
		this.drinkCategoryName = drinkCategoryName;
	}

	public String getDrinkItemId() {
		return drinkItemId;
	}

	public void setDrinkItemId(String drinkItemId) {
		this.drinkItemId = drinkItemId;
	}

	public String getDrinkItemName() {
		return drinkItemName;
	}

	public void setDrinkItemName(String drinkItemName) {
		this.drinkItemName = drinkItemName;
	}

	public String getDrinkItemLowPrice() {
		return drinkItemLowPrice;
	}

	public void setDrinkItemLowPrice(String drinkItemLowPrice) {
		this.drinkItemLowPrice = drinkItemLowPrice;
	}

	public String getDrinkItemHighPrice() {
		return drinkItemHighPrice;
	}

	public void setDrinkItemHighPrice(String drinkItemHighPrice) {
		this.drinkItemHighPrice = drinkItemHighPrice;
	}

	public String getDrinkItemPrice() {
		return drinkItemPrice;
	}

	public void setDrinkItemPrice(String drinkItemPrice) {
		this.drinkItemPrice = drinkItemPrice;
	}

	public String getDrinkItemCost() {
		return drinkItemCost;
	}

	public void setDrinkItemCost(String drinkItemCost) {
		this.drinkItemCost = drinkItemCost;
	}

	public String getDrinkItemTaxSlot() {
		return drinkItemTaxSlot;
	}

	public void setDrinkItemTaxSlot(String drinkItemTaxSlot) {
		this.drinkItemTaxSlot = drinkItemTaxSlot;
	}

	public String getDrinkItemServiceTax() {
		return drinkItemServiceTax;
	}

	public void setDrinkItemServiceTax(String drinkItemServiceTax) {
		this.drinkItemServiceTax = drinkItemServiceTax;
	}

	public String getDrinkItemUnitName() {
		return drinkItemUnitName;
	}

	public void setDrinkItemUnitName(String drinkItemUnitName) {
		this.drinkItemUnitName = drinkItemUnitName;
	}

	public String getDrinkItemColor() {
		return drinkItemColor;
	}

	public void setDrinkItemColor(String drinkItemColor) {
		this.drinkItemColor = drinkItemColor;
	}

	public String getDrinkItemQty() {
		return drinkItemQty;
	}

	public void setDrinkItemQty(String drinkItemQty) {
		this.drinkItemQty = drinkItemQty;
	}

	public String getDrinkItemComment() {
		return drinkItemComment;
	}

	public void setDrinkItemComment(String drinkItemComment) {
		this.drinkItemComment = drinkItemComment;
	}

	public String getDrinkItemTax() {
		return drinkItemTax;
	}

	public void setDrinkItemTax(String drinkItemTax) {
		this.drinkItemTax = drinkItemTax;
	}

	public String getDrinkItemIdDept() {
		return drinkItemIdDept;
	}

	public void setDrinkItemIdDept(String drinkItemIdDept) {
		this.drinkItemIdDept = drinkItemIdDept;
	}

	public String getDrinkItemDescription() {
		return drinkItemDescription;
	}

	public void setDrinkItemDescription(String drinkItemDescription) {
		this.drinkItemDescription = drinkItemDescription;
	}

	public String getDrinkItemDiscountCode() {
		return drinkItemDiscountCode;
	}

	public void setDrinkItemDiscountCode(String drinkItemDiscountCode) {
		this.drinkItemDiscountCode = drinkItemDiscountCode;
	}

	public String getDrinkItemImage() {
		return drinkItemImage;
	}

	public void setDrinkItemImage(String drinkItemImage) {
		this.drinkItemImage = drinkItemImage;
	}

	public String getDrinkItemOutletId() {
		return drinkItemOutletId;
	}

	public void setDrinkItemOutletId(String drinkItemOutletId) {
		this.drinkItemOutletId = drinkItemOutletId;
	}

	public String getDrinkItemIsDeal() {
		return drinkItemIsDeal;
	}

	public void setDrinkItemIsDeal(String drinkItemIsDeal) {
		this.drinkItemIsDeal = drinkItemIsDeal;
	}

	public String getDrinkItemOptional() {
		return drinkItemOptional;
	}

	public void setDrinkItemOptional(String drinkItemOptional) {
		this.drinkItemOptional = drinkItemOptional;
	}

	public String getDrinkItemIsHappyHour() {
		return drinkItemIsHappyHour;
	}

	public void setDrinkItemIsHappyHour(String drinkItemIsHappyHour) {
		this.drinkItemIsHappyHour = drinkItemIsHappyHour;
	}

	public String getDrinkItemHappyHourRate() {
		return drinkItemHappyHourRate;
	}

	public void setDrinkItemHappyHourRate(String drinkItemHappyHourRate) {
		this.drinkItemHappyHourRate = drinkItemHappyHourRate;
	}

	public String getDrinkItemMinRate() {
		return drinkItemMinRate;
	}

	public void setDrinkItemMinRate(String drinkItemMinRate) {
		this.drinkItemMinRate = drinkItemMinRate;
	}

	public String getDrinkItemMaxRate() {
		return drinkItemMaxRate;
	}

	public void setDrinkItemMaxRate(String drinkItemMaxRate) {
		this.drinkItemMaxRate = drinkItemMaxRate;
	}

	public String getDrinkItemCurrentRate() {
		return drinkItemCurrentRate;
	}

	public void setDrinkItemCurrentRate(String drinkItemCurrentRate) {
		this.drinkItemCurrentRate = drinkItemCurrentRate;
	}

	public String getDrinkItemLastRate() {
		return drinkItemLastRate;
	}

	public void setDrinkItemLastRate(String drinkItemLastRate) {
		this.drinkItemLastRate = drinkItemLastRate;
	}

	public String getDrinkItemstarttime() {
		return drinkItemstarttime;
	}

	public void setDrinkItemstarttime(String drinkItemstarttime) {
		this.drinkItemstarttime = drinkItemstarttime;
	}

	public String getDrinkItemcurrenttime() {
		return drinkItemcurrenttime;
	}

	public void setDrinkItemcurrenttime(String drinkItemcurrenttime) {
		this.drinkItemcurrenttime = drinkItemcurrenttime;
	}

	public Component(String drinkCategoryId, String drinkCategoryName, String drinkItemId, String drinkItemName,
					 String drinkItemCost, String drinkItemTaxSlot, String drinkItemServiceTax, String drinkItemUnitName,
					 String drinkItemColor, String drinkItemQty, String drinkItemComment, String drinkItemTax,
					 String drinkItemIdDept, String drinkItemDescription, String drinkItemDiscountCode, String drinkItemImage,
					 String drinkItemOutletId, String drinkItemIsDeal, String drinkItemOptional, String drinkItemIsHappyHour,
					 String drinkItemHappyHourRate, String drinkItemMinRate, String drinkItemMaxRate, String drinkItemCurrentRate,
					 String drinkItemLastRate, String drinkItemstarttime, String drinkItemcurrenttime) {
		super();
		this.drinkCategoryId = drinkCategoryId;
		this.drinkCategoryName = drinkCategoryName;
		this.drinkItemId = drinkItemId;
		this.drinkItemName = drinkItemName;
		this.drinkItemCost = drinkItemCost;
		this.drinkItemTaxSlot = drinkItemTaxSlot;
		this.drinkItemServiceTax = drinkItemServiceTax;
		this.drinkItemUnitName = drinkItemUnitName;
		this.drinkItemColor = drinkItemColor;
		this.drinkItemQty = drinkItemQty;
		this.drinkItemComment = drinkItemComment;
		this.drinkItemTax = drinkItemTax;
		this.drinkItemIdDept = drinkItemIdDept;
		this.drinkItemDescription = drinkItemDescription;
		this.drinkItemDiscountCode = drinkItemDiscountCode;
		this.drinkItemImage = drinkItemImage;
		this.drinkItemOutletId = drinkItemOutletId;
		this.drinkItemIsDeal = drinkItemIsDeal;
		this.drinkItemOptional = drinkItemOptional;
		this.drinkItemIsHappyHour = drinkItemIsHappyHour;
		this.drinkItemHappyHourRate = drinkItemHappyHourRate;
		this.drinkItemMinRate = drinkItemMinRate;
		this.drinkItemMaxRate = drinkItemMaxRate;
		this.drinkItemCurrentRate = drinkItemCurrentRate;
		this.drinkItemLastRate = drinkItemLastRate;
		this.drinkItemstarttime = drinkItemstarttime;
		this.drinkItemcurrenttime = drinkItemcurrenttime;

	}
	
	/*public Component(String itemCode2, String itemName2, String itemPrice2, String itemQty2) {

		this.name = name2;
		this.description=description2;
		this.cost=cost2;
		// this.icode = icode2;
		this.qty=qty2;
		this.id=id2;
		this.color=color2;
		this.quantity=quantity2;
		this.comment=comment2;
		this.idnew=idnew2;
		this.supdeptid=supdeptid2;
		this.supdeptname=supdeptname2;
		this.tax=tax2;
		this.sattlement_bill_amount=sattlement_bill_amount2;
		this.sattlement_bill_number=sattlement_bill_number2;
		this.sattlement_Change_amount=sattlement_Change_amount2;
		this.sattlement_Grand_Total=sattlement_Grand_Total2;
		this.sattlement_Name=sattlement_Name2;
		this.item_image_id =item_image_id2;
	}*/
	
}
