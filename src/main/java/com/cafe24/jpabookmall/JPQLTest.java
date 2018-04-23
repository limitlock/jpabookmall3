package com.cafe24.jpabookmall;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.cafe24.jpabookmall.dto.BookDTO;
import com.cafe24.jpamall.domain.Category;

public class JPQLTest {

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
			// testTypedQuery(em);
			// testQuery(em);
			// testNamedParameter(em);
			// testSQLInjection(em);
			// testProjection(em);

			// testDTO(em);
			testDTO2(em);

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

	// DTO 사용 (new 명령어 사용)
	public static void testDTO2(EntityManager em) {
		String jpql = "select new com.cafe24.jpabookmall.dto.BookDTO(b.no, b.title) from Book b";
		TypedQuery<BookDTO> query = em.createQuery(jpql, BookDTO.class);

		List<BookDTO> resultDTOs = query.getResultList();

		for (BookDTO dto : resultDTOs) {
			System.out.println(dto);
		}

	}

	// DTO 사용
	public static void testDTO(EntityManager em) {
		String jpql = "select b.no, b.title from Book b";
		Query query = em.createQuery(jpql);

		List<Object[]> rows = query.getResultList();
		List<BookDTO> resultDTOs = new ArrayList<BookDTO>();

		for (Object[] row : rows) {
			BookDTO dto = new BookDTO();
			dto.setNo((Long) row[0]);
			dto.setTitle((String) row[1]);

			resultDTOs.add(dto);
		}

		for (BookDTO dto : resultDTOs) {
			System.out.println(dto);
		}

	}

	// Scalar + Entity Projection
	public static void testProjection(EntityManager em) {
		String jpql = "select b.title, b.category from Book b";
		Query query = em.createQuery(jpql);

		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			System.out.println(row[0] + ":" + row[1]);
		}

	}

	// SQL Injection Test
	public static void testSQLInjection(EntityManager em) {
		// String title = "'babaoy or 1=1";
		String jpql = "select b.no, b.title from Book b";
		Query query = em.createQuery(jpql);

		// query.setParameter("title", title);

		List<Object> list = query.getResultList();
		for (Object object : list) {
			Object[] results = (Object[]) object;
			for (Object result : results) {
				System.out.print(result + ":");
			}
			System.out.println();
		}

	}

	// Named Parameter (이름 기반)
	public static void testNamedParameter(EntityManager em) {
		String jpql = "select c from Category c where name = :name";
		TypedQuery<Category> query = em.createQuery(jpql, Category.class);
		query.setParameter("name", "카테고리1");

		List<Category> list = query.getResultList();
		for (Category category : list) {
			System.out.println(category);
		}
	}

	// 위치기반
	public static void testNamedParameter2(EntityManager em) {
		String jpql = "select c from Category c where name = ?1";
		TypedQuery<Category> query = em.createQuery(jpql, Category.class);
		query.setParameter(1, "카테고리1");

		List<Category> list = query.getResultList();
		for (Category category : list) {
			System.out.println(category);
		}
	}

	// TypedQuery
	// Entity Projection
	public static void testTypedQuery(EntityManager em) {
		String jpql = "SELECT c FROM Category c";

		TypedQuery<Category> query = em.createQuery(jpql, Category.class);
		List<Category> list = query.getResultList();
		for (Category category : list) {
			System.out.println(category);
		}
	}

	// Non Typed Query
	// Scalar Projection
	public static void testQuery(EntityManager em) {
		String jpql = "select c.no, c.name from Category c";
		Query query = em.createQuery(jpql);

		List<Object> list = query.getResultList();
		for (Object object : list) {
			Object[] results = (Object[]) object;
			for (Object result : results) {
				System.out.print(result + ":");
			}
			System.out.println();
		}
	}

}
