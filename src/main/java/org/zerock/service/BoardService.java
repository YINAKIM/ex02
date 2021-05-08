package org.zerock.service;

import org.zerock.domain.BoardVO;

import java.util.List;

public interface BoardService {

    public void register(BoardVO board);
    public BoardVO get(Long bno);
    public boolean modify(BoardVO board);  // 수정 성공하면 true리턴, int로 받을 수 있지만 정확히 하려고 bool로 리턴하도록 했음
    public boolean remove(Long bno);
    public List<BoardVO> getList();
}
