package com.kelly.logic.manager.inter;

import java.util.Date;
import java.util.List;

import com.kelly.dto.RecordDTO;

public interface IRecordManager {

	/**
	 * 将符合条件的详单记录返回
	 * 
	 */
	public List<RecordDTO> searchRecords(Date startDate, Date endDate);
}
