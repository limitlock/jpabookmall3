package com.cafe24.jpamall.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity(name = "Book")
@Table(name = "book") // db안에 있는 book 테이블에 매핑된다.
public class Book {

	@Id // 주키(primary key) 표시
	@Column(name = "no") // 컬럼 매핑
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long no;

	@Column(name = "title", nullable = false, length = 200)
	private String title;

	@Column(name = "price", nullable = false)
	private Integer price;

	@Enumerated(EnumType.STRING)
	@Column(name = "kind", nullable = false, columnDefinition = "enum('A','B','C')")
	private Kind kind;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "reg_date", nullable = false)
	private Date regDate;

	@Lob
	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "discount_rate", nullable = true)
	private Integer discountRate;

	@Transient
	private Integer discountPrice;

	@ManyToOne
	@JoinColumn(name = "category_no", nullable = true) // <--필수는 아니다. 자동으로 '필드명 + “_” + 참조하는 테이블의 기본 키(@Id) 컬럼명'를 생성한다.
	private Category category;

	public Category getCategory() {
		return category;
	}

	
	
	
	/**
	 * 중요하다.
	 * @param category
	 */
	public void setCategory(Category category) {
		if (this.category != null) {
			this.category.getBooks().remove(this);
		}

		this.category = category;
		
		
		if (category != null) {
			category.getBooks().add(this);
		}
	}
	
	
	

	public Integer getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Integer discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book [no=" + no + ", title=" + title + ", price=" + price + ", kind=" + kind + ", regDate=" + regDate
				+ ", description=" + description + ", discountRate=" + discountRate + ", discountPrice=" + discountPrice
				+ ", category=" + category + "]";
	}

}
