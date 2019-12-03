////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑          永无BUG         永不修改              //
////////////////////////////////////////////////////////////////////
package com.example.service;

import com.example.dao.MoviesRepository;
import com.example.entity.Movies;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author: 周未
 * @Description:
 * @create 14:40 2019/11/26
 */
@Service
public class MoviesService {
	@Autowired
	MoviesRepository moviesRepository;

	public  int pachong_page(String url) throws IOException {

		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla").get();//模拟火狐浏览器
		} catch (IOException e) {
			e.printStackTrace();
		}
		//这里根据在网页中分析的类选择器来获取电影列表所在的节点
		Elements div = doc.getElementsByClass("co_content8");
		//获取电影列表
		Elements table = div.select("table");//查找table标签
		//获取电影列表总数
		int result = table.size();
		Integer _id = 0;
		//System.out.println("电影列表数为:"+result);
		for (Element tb : table) {
			++_id;
			try {
				Thread.sleep(1000);//让线程操作不要太快 1秒一次 时间自己设置，主要是模拟人在点击
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//获取所有电影详情的链接所在的节点
			Elements tr = tb.select("tr");
			//System.out.println(tr.size());
			//获取电影列表链接和标题
			String videos = tr.get(1).select("a").attr("abs:href");
			String originIntroductionUrl = tr.get(1).select("a").attr("abs:href");
			System.out.println("源介绍页面: "+originIntroductionUrl);
			String brief = tr.get(1).select("a").text();
			System.out.println("简介: "+brief);
			//这里要跳过这个首页页面 否则会抛出异常
			if ("http://www.dytt8.net/html/gndy/jddy/index.html".equals(videos)) {
				continue;
			}
			//进如电影列表详情页面
			doc = Jsoup.connect(videos).userAgent("Mozilla").get();
			//获取到电影详情页面所在的节点
			Element div1 = doc.getElementById("Zoom");
			//获取电影描述
			String description = div1.select("p").text();
			System.out.println("电影描述"+description);
			//获取封面图地址
			Elements select = div1.select("img[src$=.jpg]");
			String  imgUrl = "";
			String  downloadUrl = "";
			if(select.size()>0) {
				imgUrl = select.get(0).attr("abs:src");
				System.out.println("图片地址：" + imgUrl);
				//获取下载地址
				downloadUrl = div1.select("td").text();
				System.out.println("下载地址：" + downloadUrl);
			}

			System.out.println("=================爬取一条结束===============================");
			//存入数据库
			Movies movies=movies = new Movies(_id,originIntroductionUrl,brief,description,imgUrl,downloadUrl);
			moviesRepository.save(movies);
		}
		return result;
	}
}
