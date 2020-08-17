package com.geekbrains.book.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookStoreApplication {
	// Домашнее задание:
	// 1. Добавить на html странице навигацию по страницам. Находясь
	// на первом странице кнопка prev должна быть неактивна, то же
	// самое и для кнопки next. При переходе между страницами фильтр
	// не должен сбрасываться
	// [ Панель навигации можно брать отсюда:
	// https://getbootstrap.com/docs/4.5/components/pagination/ ]
	// 2. Добавьте форму для фильтра, после фильтрации форма не должна
	// сбрасываться
	// 3. Добавьте каждой книге жанр: Фэнтези, Фантастика, Детектив
	// в виде enum. Сделайте фильтр по одному или нескольким жанрам.
	// Жанры выбираются как checkbox'ы. Упрощение: книга имеет только
	// один жанр

	public static void main(String[] args) {
		SpringApplication.run(BookStoreApplication.class, args);
	}
}
