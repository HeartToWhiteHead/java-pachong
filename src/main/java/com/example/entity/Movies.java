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
package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author: 周未
 * @Description:
 * @create 14:28 2019/11/26
 */
@Document (collection = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movies {

	@Id
	@Field(name = "_id")
	private Integer _id;


	//源介绍页面
	@Field(name = "originIntroductionUrl")
	private String originIntroductionUrl;

	//简介
	@Field(name = "brief")
	private String brief;

	//电影描述
	@Field(name = "description")
	private String description;

	//图片地址
	@Field(name = "imgUrl")
	private String imgUrl;

	//下载地址
	@Field(name = "downloadUrl")
	private  String downloadUrl;
}
