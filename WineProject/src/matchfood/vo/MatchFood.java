package matchfood.vo;

import org.apache.ibatis.type.Alias;

@Alias("MatchFood")
public class MatchFood {
	private int matchFoodId;
	private String matchFoodName;
	private int price;
	private String nation;
	private String img;
	private String wineImg;
	private String weight;
	private String ex;
	
	public MatchFood() {}

	public MatchFood(int matchFoodId, String matchFoodName, int price, String nation, String img, String wineImg,
			String weight, String ex) {
		super();
		this.matchFoodId = matchFoodId;
		this.matchFoodName = matchFoodName;
		this.price = price;
		this.nation = nation;
		this.img = img;
		this.wineImg = wineImg;
		this.weight = weight;
		this.ex = ex;
	}

	public MatchFood(String matchFoodName, int price, String nation, String img, String wineImg, String weight,
			String ex) {
		super();
		this.matchFoodName = matchFoodName;
		this.price = price;
		this.nation = nation;
		this.img = img;
		this.wineImg = wineImg;
		this.weight = weight;
		this.ex = ex;
	}

	public int getMatchFoodId() {
		return matchFoodId;
	}

	public void setMatchFoodId(int matchFoodId) {
		this.matchFoodId = matchFoodId;
	}

	public String getMatchFoodName() {
		return matchFoodName;
	}

	public void setMatchFoodName(String matchFoodName) {
		this.matchFoodName = matchFoodName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getWineImg() {
		return wineImg;
	}

	public void setWineImg(String wineImg) {
		this.wineImg = wineImg;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getEx() {
		return ex;
	}

	public void setEx(String ex) {
		this.ex = ex;
	}
	
}
