package com.youku.yks.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 对List进行拆分，用于分布式计算
 * @author mengfeiyang
 *
 */
public class ListSplit {
	List<List<String[]>> eList = new ArrayList<List<String[]>>();
	List<String[]> gList;
    
	public List<List<String[]>> SplitList(List<String[]> sList, int num) {
		int size = (sList.size()) / num;
		int size2 = (sList.size()) % num;
		int j = 0;
		int xx=0;
		for (int i = 0; i < num; i++) {
			gList = new ArrayList<String[]>();

			for (j = xx; j < (size + xx); j++) {
				gList.add(sList.get(j));
			}
			xx = j;
			eList.add(gList);
		}
		if (size2 != 0) {
			gList = new ArrayList<String[]>();
			for (int y = 1; y < size2 + 1; y++) {
				gList.add(sList.get(sList.size() - y));
			}
			eList.add(gList);
		}
		return eList;
	}
}


