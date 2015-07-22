package com.kelly.logic.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kelly.base.framework.BaseDao;
import com.kelly.dto.RecordDTO;
import com.kelly.logic.manager.inter.IRecordManager;
import com.kelly.pojo.fruit.Record;

public class RecordManager implements IRecordManager {
	private BaseDao<Record> recordDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<RecordDTO> searchRecords(Date startDate, Date endDate){
		String hql = "from Record where buyDate>? and buyDate <=?";
		List<RecordDTO> recordDTOList = new ArrayList<RecordDTO>();
		
		List<Record> recordList = (List<Record>) recordDao.find(hql, startDate,endDate);
		
		for (Record record : recordList) {
			RecordDTO recordDTO = new RecordDTO(record);
		
			recordDTOList.add(recordDTO);
		}
		return recordDTOList;
	}

	public void setRecordDao(BaseDao<Record> recordDao) {
		this.recordDao = recordDao;
	}

}
