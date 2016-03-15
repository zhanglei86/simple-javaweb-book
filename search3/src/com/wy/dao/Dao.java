package com.wy.dao;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.TermPositionVector;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryFilter;

import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;



import com.wy.form.Word;
import com.wy.tools.JDBConnection;

public class Dao {
	private JDBConnection connection = null;
	private Analyzer analyzer = null;;
	public Dao() {
		analyzer = new StandardAnalyzer();

	}

	// 创建搜索引擎的路径
	private String createFolder(String path) {
		String filepath = "";
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		try {
			filepath = f.getCanonicalPath();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return filepath;
	}
	// 创建搜索引擎
	public boolean writerinNet(String path) {
		connection = new JDBConnection();
		boolean flag = false;
		String sql = "select  * from tb_net";
		try {
			
			IndexWriter iwriter = new IndexWriter(createFolder(path), analyzer,true);
			iwriter.setMaxFieldLength(25000);
			ResultSet rs = connection.executeQuery(sql);
			while (rs.next()) {
				Document doc = new Document();
				doc.add(new Field("id", rs.getString("id"), Field.Store.YES,
						Field.Index.TOKENIZED));
				doc.add(new Field("net_title", rs.getString("net_title"),
						Field.Store.YES, Field.Index.TOKENIZED,
						Field.TermVector.WITH_POSITIONS_OFFSETS));
				doc.add(new Field("net_content", rs.getString("net_content"),
						Field.Store.YES, Field.Index.TOKENIZED,
						Field.TermVector.WITH_POSITIONS_OFFSETS));
				doc.add(new Field("address", rs.getString("address"),
						Field.Store.YES, Field.Index.TOKENIZED));
				iwriter.addDocument(doc);
			}
			iwriter.close();
			flag = true;
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			connection.closeConnection();
		}
		return flag;
	}

	// 搜索引擎的查询

	public Hits readinNet(String queryString, String path) {
		Hits hits = null;
		Query query = null;
		try {

			String field[] = {"net_title", "net_content"};
			String quString[] = {queryString, queryString};
			query = MultiFieldQueryParser.parse(quString, field, analyzer);
			hits = bigLight(query, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hits;
	}
	
	//在结果中进行搜索           这个方法并没有应用
	public Hits searchInResult(String qw,String oldqw,String path){		
		Hits hits = null;
		Query query = null;
		Query oldquery = null;
		try {
			String field[] = {"net_title", "net_content"};
			
			String quString[] = {qw, qw};			
			query = MultiFieldQueryParser.parse(quString, field, analyzer);		
			
					
			String oldquString[] = {oldqw, oldqw};			
			oldquery = MultiFieldQueryParser.parse(oldquString, field, analyzer);
			
			QueryFilter oldFiter=new QueryFilter(oldquery);
			CachingWrapperFilter filter = new CachingWrapperFilter(oldFiter);
			
			
			
			hits = bigLight(query, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hits;
	}
	
	
	
	
	
	
	

	private Hits bigLight(Query query, String path)
			throws CorruptIndexException, IOException {
		IndexSearcher is = new IndexSearcher(createFolder(path));
		Hits hits = is.search(query);		
		SimpleHTMLFormatter shformat = new SimpleHTMLFormatter(
				"<b><font color=\"red\">", "</font></b>");// 高亮显示的格式化;
		Highlighter highlit = new Highlighter(shformat, new QueryScorer(query));// 用查询关建字创建高亮显示对象
		for (int i = 0; i < hits.length(); i++) {
			Document dochit = hits.doc(i);

			String content = dochit.get("net_content");
			TokenStream contentTokenstream = analyzer.tokenStream(
					"net_content", new StringReader(content));// Token是索引时候的基本单元，代表一个被索引的词,所有包含中文的文本都必须是Tokenized的。
			String contentResult = highlit.getBestFragment(contentTokenstream,
					content);

			if (contentResult != null) {
				Field contentField = new Field("net_content", contentResult,
						Field.Store.YES, Field.Index.TOKENIZED);
				dochit.removeField("net_content");
				dochit.add(contentField);
			}
			
			
			
			
			String title = dochit.get("net_title");
			TokenStream titleTokenstream = analyzer.tokenStream("net_title",
					new StringReader(title));
			String titleResult = highlit.getBestFragment(titleTokenstream,
					title);
			if (titleResult != null) {
				Field titleField = new Field("net_title", titleResult,
						Field.Store.YES, Field.Index.TOKENIZED);
				dochit.removeField("net_title");
				dochit.add(titleField);
			}
		}
		return hits;
	}

	private Word wordForm(String wordname) {
		connection = new JDBConnection();
		Word word = null;
		String sql = "select * from tb_word where word_name = '" + wordname
				+ "'";
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				word = new Word();
				word.setId(rs.getInt(1));
				word.setWord_name(rs.getString(2));
				word.setWord_number(rs.getInt(3));
				word.setWord_result(rs.getInt(4));
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} finally {
			connection.closeConnection();
		}
		return word;
	}

	public void addWordForm(String wordname, Integer result) {
		Word word = this.wordForm(wordname);
		String sql = "";
		if (word == null) {
			sql = "insert into tb_word values ('" + wordname + "',1," + result
					+ ")";
		} else {
			sql = "update tb_word set word_number = word_number + 1 where id = "
					+ word.getId() + "";
		}
		connection = new JDBConnection();
		connection.executeUpdate(sql);
		connection.closeConnection();
	}

	public void updateWordFomr(Word word) {
		String sql = "update tb_word set word_result = "
				+ word.getWord_result() + " where id = " + word.getId() + "";
		connection = new JDBConnection();
		connection.executeUpdate(sql);
		connection.closeConnection();
	}

	public List wordList(String desc) {
		connection = new JDBConnection();
		List list = new ArrayList();
		Word word = null;
		String sql = "select * from tb_word order by id desc";
		if (desc != null) {
			sql = "select * from tb_word order by word_number desc";

		}
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				word = new Word();
				word.setId(rs.getInt(1));
				word.setWord_name(rs.getString(2));
				word.setWord_number(rs.getInt(3));
				word.setWord_result(rs.getInt(4));
				list.add(word);
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} finally {
			connection.closeConnection();
		}
		return list;
	}

	public List soWordList(String name) {
		connection = new JDBConnection();
		List list = new ArrayList();
		Word word = null;
		String sql = "select * from tb_word where word_name like '%" + name
				+ "%'";
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				word = new Word();
				word.setId(rs.getInt(1));
				word.setWord_name(rs.getString(2));
				word.setWord_number(rs.getInt(3));
				word.setWord_result(rs.getInt(4));
				list.add(word);
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} finally {
			connection.closeConnection();
		}
		return list;
	}

}
