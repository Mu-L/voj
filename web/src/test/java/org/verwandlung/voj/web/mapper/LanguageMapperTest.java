package org.verwandlung.voj.web.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import org.verwandlung.voj.web.model.Language;

/**
 * LanguageMapper测试类.
 * @author Haozhe Xie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration({"classpath:test-spring-context.xml"})
public class LanguageMapperTest {
	/**
	 * 测试用例: 测试getLanguageUsingId(int)方法
	 * 测试数据: C语言的编程语言唯一标识符
	 * 预期结果: 返回C语言的编程语言对象
	 */
	@Test
	public void testGetLanguageUsingIdExists() {
		Language language = languageMapper.getLanguageUsingId(1);
		Assert.assertNotNull(language);
		
		String languageName = language.getLanguageName();
		Assert.assertEquals("C", languageName);
	}
	
	/**
	 * 测试用例: 测试getLanguageUsingId(int)方法
	 * 测试数据: 不存在的编程语言唯一标识符
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetLanguageUsingIdNotExists() {
		Language language = languageMapper.getLanguageUsingId(0);
		Assert.assertNull(language);
	}
	
	/**
	 * 测试用例: 测试getLanguageUsingSlug(String)方法
	 * 测试数据: C语言的编程语言别名
	 * 预期结果: 返回C语言的编程语言对象
	 */
	@Test
	public void testGetLanguageUsingSlugExists() {
		Language language = languageMapper.getLanguageUsingSlug("text/x-csrc");
		Assert.assertNotNull(language);
		
		String languageName = language.getLanguageName();
		Assert.assertEquals("C", languageName);
	}
	
	/**
	 * 测试用例: 测试getLanguageUsingSlug(String)方法
	 * 测试数据: 不存在的编程语言别名
	 * 预期结果: 返回空引用
	 */
	@Test
	public void testGetLanguageUsingSlugNotExists() {
		Language language = languageMapper.getLanguageUsingSlug("Not-Exists");
		Assert.assertNull(language);
	}
	
	/**
	 * 测试用例: 测试getAllLanguages()方法
	 * 测试数据: N/a
	 * 预期结果: 返回全部的编程语言列表(共6种语言)
	 */
	@Test
	public void testGetAllLanguages() {
		List<Language> languages = languageMapper.getAllLanguages();
		Assert.assertNotNull(languages);
		Assert.assertEquals(6, languages.size());
		
		Language firstLanguage = languages.get(0);
		Assert.assertNotNull(firstLanguage);
		
		String languageName = firstLanguage.getLanguageName();
		Assert.assertEquals("C", languageName);
	}
	
	/**
	 * 测试用例: 测试createLanguage(Language)方法
	 * 测试数据: 合法的数据集
	 * 预期结果: 数据插入操作成功完成
	 */
	@Test
	public void testCreateLanguageNormally() {
		Language language = new Language("text/x-php", "PHP", "php foo.php", "php foo.php");
		int numberOfRowsAffected = languageMapper.createLanguage(language);
		Assert.assertEquals(1, numberOfRowsAffected);
	}
	
	/**
	 * 测试用例: 测试createLanguage(Language)方法
	 * 测试数据: 不合法的数据集(过长的编程语言英文缩写)
	 * 预期结果: 抛出DataIntegrityViolationException异常
	 */
	@Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
	public void testCreateLanguageUsingTooLongSlug() {
		Language language = new Language("TooLongLanguageSlug", "Invalid Langauge", "Compile Command", "Run Command");
		languageMapper.createLanguage(language);
	}
	
	/**
	 * 测试用例: 测试updateLanguage(Language)方法
	 * 测试数据: 合法的数据集, 且数据表中存在对应ID的记录
	 * 预期结果: 数据更新操作成功完成
	 */
	@Test
	public void testUpdateLanguageNormally() {
		Language language = languageMapper.getLanguageUsingId(2);
		Assert.assertNotNull(language);
		
		language.setLanguageName("D");
		int numberOfRowsAffected = languageMapper.updateLanguage(language);
		Assert.assertEquals(1, numberOfRowsAffected);
		
		language = languageMapper.getLanguageUsingId(2);
		Assert.assertEquals("D", language.getLanguageName());
	}
	
	/**
	 * 测试用例: 测试updateLanguage(Language)方法
	 * 测试数据: 合法的数据集, 但数据表中不存在该编程语言
	 * 预期结果: 方法正常执行, 未影响数据表中的数据
	 */
	@Test
	public void testUpdateLanguageNotExists() {
		Language language = new Language(0, "not-exist", "Not Exist", "Not Exist", "Not Exist");
		int numberOfRowsAffected = languageMapper.updateLanguage(language);
		Assert.assertEquals(0, numberOfRowsAffected);
	}
	
	/**
	 * 测试用例: 测试deleteLanguage(int)方法
	 * 测试数据: Ruby语言的编程语言唯一标识符
	 * 预期结果: 数据删除操作成功完成
	 */
	@Test
	public void testDeleteLanguageExists() {
		Language language = languageMapper.getLanguageUsingId(6);
		Assert.assertNotNull(language);
		
		int numberOfRowsAffected = languageMapper.deleteLanguage(6);
		Assert.assertEquals(1, numberOfRowsAffected);
		
		language = languageMapper.getLanguageUsingId(6);
		Assert.assertNull(language);
	}
	
	/**
	 * 测试用例: 测试deleteLanguage(int)方法
	 * 测试数据: 不存在的编程语言唯一标识符
	 * 预期结果: 方法正常执行, 未影响数据表中的数据
	 */
	@Test
	public void testDeleteLanguageNotExists() {
		Language language = languageMapper.getLanguageUsingId(0);
		Assert.assertNull(language);
		
		int numberOfRowsAffected = languageMapper.deleteLanguage(0);
		Assert.assertEquals(0, numberOfRowsAffected);
	}
	
	/**
	 * 待测试的LanguageMapper对象.
	 */
	@Autowired
	private LanguageMapper languageMapper;
}
