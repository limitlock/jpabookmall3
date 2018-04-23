package com.cafe24.jpabookmall;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.cafe24.jpamall.domain.Book;
import com.cafe24.jpamall.domain.Category;
import com.cafe24.jpamall.domain.Kind;

public class App {
	public static void main(String[] args) {

		// 1. Entity Manager Factory 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabookmall3"); // db

		// 2. Entity Manager 생성
		EntityManager em = emf.createEntityManager();

		// 3.Get TX
		EntityTransaction tx = em.getTransaction();

		// 4. TX Begins
		tx.begin();

		// 5. Business Logic
		try {

			// 1.데이터 저장
			// testInsertCateroies(em);
			// testInsertBooks(em);

			// 2.양방향 관계 조회 private List<Book> books;
			// testFindBooksByCategory(em);

			// testSave(em);

			// 저장(가짜매핑,주인이 아닌 엔티티가 관계를 저장, 외래키 세팅이 안된다. 오류) Book(0) Category(X)
			// testSaveNonOwner(em);

			testSaveBug(em);

		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		// 6. TX Commit
		tx.commit();

		// 7. Entity Manager 종료
		em.clear();

		// 8. Entity Manager Factory 종료
		emf.close();

	}

	public static void testSaveBug(EntityManager em) {
		Category category10 = new Category();
		category10.setName("카테고리10");
		em.persist(category10);

		Category category20 = new Category();
		category20.setName("카테고리20");
		em.persist(category20);

		Book book10 = new Book();
		book10.setTitle("책10");
		book10.setPrice(10000);
		book10.setKind(Kind.C);
		book10.setRegDate(new Date());

		em.persist(book10);

		// 참조하는 카테고리를 바꿀때, 기존 카테고리에 들어있는 자기자신(객체)를 제거해줘야한다.
		book10.setCategory(category10);
		book10.setCategory(category20);

	}

	public static void testSaveNonOwner(EntityManager em) { // 에러

		Book book3 = new Book();
		book3.setTitle("책3");
		book3.setPrice(10000);
		book3.setKind(Kind.C);
		book3.setRegDate(new Date());

		em.persist(book3);

		Book book4 = new Book();
		book4.setTitle("책4");
		book4.setPrice(10000);
		book4.setKind(Kind.C);
		book4.setRegDate(new Date());

		em.persist(book4);

		Category category = new Category();
		category.setName("카테고리1");
		category.getBooks().add(book3);
		category.getBooks().add(book4);

		em.persist(category);

	}

	public static void testSave(EntityManager em) {
		Category category = new Category();
		category.setName("카테고리1");

		em.persist(category);

		Book book1 = new Book();
		book1.setTitle("책1");
		book1.setPrice(10000);
		book1.setKind(Kind.A);
		book1.setRegDate(new Date());
		book1.setDescription("자바책입니다.");
		book1.setCategory(category);

		em.persist(book1);

		Book book2 = new Book();
		book2.setTitle("책2");
		book2.setPrice(20000);
		book2.setKind(Kind.B);
		book2.setRegDate(new Date());
		book2.setDescription("자바책입니다.");
		book2.setCategory(category);

		em.persist(book2);

		List<Book> list = category.getBooks();
		for (Book book : list) {
			System.out.println(book);
		}

	}

	public static void testFindBooksByCategory(EntityManager em) {

		Category category = em.find(Category.class, 1L);
		System.out.println(category);

		List<Book> list = category.getBooks();
		for (Book book : list) {
			System.out.println(book);
		}

	}

	public static void testInsertBooks(EntityManager em) {
		Category category1 = em.find(Category.class, 1L);
		Book book1 = new Book();
		book1.setTitle("자바의신");
		book1.setPrice(10000);
		book1.setKind(Kind.A);
		book1.setRegDate(new Date());
		book1.setDescription("자바책입니다.");
		book1.setCategory(category1);

		em.persist(book1);

		// Category category2 = em.find(Category.class, 2L);
		Book book2 = new Book();
		book2.setTitle("Spring in Action");
		book2.setPrice(20000);
		book2.setKind(Kind.B);
		book2.setRegDate(new Date());
		book2.setDescription("스프링책입니다.");
		book2.setCategory(category1);

		em.persist(book2);

		Category category3 = em.find(Category.class, 3L);
		Book book3 = new Book();
		book3.setTitle("C 프로그래밍");
		book3.setPrice(30000);
		book3.setKind(Kind.C);
		book3.setRegDate(new Date());
		book3.setDescription("C 책입니다.");
		book3.setCategory(category3);

		em.persist(book3);
	}

	public static void testInsertCateroies(EntityManager em) {

		// 1L
		Category category1 = new Category();
		category1.setName("자바 프로그래밍");
		em.persist(category1);

		// 2L
		Category category2 = new Category();
		category2.setName("스프링 프로그래밍");
		em.persist(category2);

		// 3L
		Category category3 = new Category();
		category3.setName("C 프로그래밍");
		em.persist(category3);
	}

}
