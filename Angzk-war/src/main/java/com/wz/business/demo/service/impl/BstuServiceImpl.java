package com.wz.business.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wz.business.demo.service.BstuService;
import com.wz.mapper.BstuMapper;
import com.wz.model.Bstu;
import com.wz.model.BstuExample;

/**
 * 
 *
 * @ClassName:  BstuServiceImpl   
 * @Description:TODO 测试impl
 * @author: Angkz
 * @date:   2018年3月14日 下午2:53:47   
 *
 */
@Service
public class BstuServiceImpl implements BstuService {
	
	@Autowired
	private BstuMapper bstuMapper;
	
	@Override
	public List<Bstu> findAllBstu() {
		// TODO Auto-generated method stub
		BstuExample example  = new BstuExample();
		List<Bstu> list = bstuMapper.selectByExample(example);
		return list;
	}

	@Override
	public void task1() {
		// TODO Auto-generated method st
		System.err.println("任务1");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void task2() {
		// TODO Auto-generated method stub
		System.err.println("任务2");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void task3() {
		// TODO Auto-generated method stub
		System.err.println("任务3");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
