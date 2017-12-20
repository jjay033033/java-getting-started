/**
 * 
 */
package top.lmoon.myspider.idfactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import top.lmoon.myspider.util.CommonUtil;

/**
 * @author LMoon
 * @date 2017年9月30日
 * 
 */
public class SongIdFactory {

	private static ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();

	// private static AtomicInteger counter = new AtomicInteger(1);

	private SongIdFactory() {
	}

	public static SongIdFactory getInstance() {
		return SongIdFactoryHolder.SONG_ID_FACTORY;
	}
	
	private static class SongIdFactoryHolder{
		private static final SongIdFactory SONG_ID_FACTORY = new SongIdFactory();
	}

	public String getSongId() {
		return CommonUtil.getUUID();
	}

	public int getSongIdForSinger(int singerId) {
		int id = 1;
		synchronized (this) {
			Integer preId = map.get(singerId);
			if(preId!=null){
				id = preId+1;
			}
			map.put(singerId, id);
		}
		return id;
	}

}
