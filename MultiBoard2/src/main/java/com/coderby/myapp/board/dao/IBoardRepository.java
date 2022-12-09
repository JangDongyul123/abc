package com.coderby.myapp.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.coderby.myapp.board.model.Board;
import com.coderby.myapp.board.model.BoardUploadFile;

public interface IBoardRepository {
	int selectMaxArticleNo();
	int selectMaxFileId();
	
	void insertArticle(Board board);
	void insertFileData(BoardUploadFile file);
	
	List<Board> selectArticleListByCategory(@Param("categoryId") int categoryId, @Param("start") int start, @Param("end") int end);
	
	Board selectArticle(int boardId);
	BoardUploadFile getFile(int fileId);
		
	void updateReadCount(int boardId);

	void updateReplyNumber(@Param("masterId") int masterId, @Param("replyNumber") int replyNumber);
	void replyArticle(Board board);
	
	String getPassword(int boardId);
	
	void updateArticle(Board board);
	void updateFileData(BoardUploadFile file);
	
	void deleteFileData(int boardId);
	void deleteReplyFileData(int boardId);
	Board selectDeleteArticle(int boardId);
	void deleteArticleByBoardId(int boardId);
	void deleteArticleByMasterId(int boardId);
	
	int selectTotalArticleCount();
	int selectTotalArticleCountByCategoryId(int categoryId);
	List<Board> searchListByContentKeyword(@Param("keyword") String keyword, @Param("start") int start, @Param("end") int end);
	int selectTotalArticleCountByKeyword(String keyword);
}