package cn.edu.zhku.shopping.store.goods.web.servlet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.edu.zhku.shopping.category.domain.Category;
import cn.edu.zhku.shopping.goods.domain.Goods;
import cn.edu.zhku.shopping.store.goods.service.StoreGoodsService;
import cn.edu.zhku.shopping.store.store.domain.Store;
import cn.itcast.commons.CommonUtils;

public class StoreGoodsAddServlet extends HttpServlet {
	/**
	 * 添加商品第二步  ----文件上传
	 * 
	 * 上传文件这里卡了好久
	 * 因为，debug的时候，出现上传空值的情况！
	 * 后来添加了List<FileItem> fileItemList2 = null;  上传文件表单链表
	 * 解决了空值问题
	 * 并暴露了问题： list.get(0) 时从下标0开始取值的！

	 */
	@SuppressWarnings("unchecked")	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		// 1. commons-fileupload的上传三步
		// 创建工具
		FileItemFactory factory = new DiskFileItemFactory();
		//2. 创建解析器对象
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setFileSizeMax(80 * 1024);//设置单个上传的文件上限为80KB
		//3. 解析request得到List<FileItem>
		List<FileItem> fileItemList = null;
		
		/**
		 * 上传文件这里卡了好久
		 * 因为，debug的时候，出现上传空值的情况！
		 * 后来添加了List<FileItem> fileItemList2 = null;  上传文件表单链表
		 * 解决了空值问题
		 * 并暴露了问题： list.get(0) 时从下标0开始取值的！
		 */
		List<FileItem> fileItemList2 = null;
		try {
			fileItemList = sfu.parseRequest(request);
			fileItemList2 =  sfu.parseRequest(request);
			fileItemList2.clear();
		} catch (FileUploadException e) {
			// 如果出现这个异步，说明单个文件超出了80KB
			error("上传的文件超出了80KB", request, response);
			return;
		}
		
		 // 4. 把List<FileItem>封装到Goods对象中
		 //首先把“普通表单字段”放到一个Map中，再把Map转换成Goods和Category,store对象，再建立三者的关系
		Map<String,Object> map = new HashMap<String,Object>();
		for(FileItem fileItem : fileItemList) {
			if(fileItem.isFormField()) {//如果是普通表单字段
				map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
			}
			else{
				fileItemList2.add(fileItem);
			}
		}
	    Goods goods = CommonUtils.toBean(map, Goods.class);//把Map中大部分数据封装到goods对象中
		Category category = CommonUtils.toBean(map, Category.class);//把Map中cid封装到Category中
		goods.setCategory(category);
		Store store=CommonUtils.toBean(map, Store.class);//把Map中sid封装到store中
		goods.setStore(store);
		
		//处理大图，然后处理小图
		
		/*
		 * 把上传的图片保存起来
		 * 思路：
		 * 1获取文件名：截取,只需要截取就行
		 * 2给文件添加前缀：使用uuid前缀，为也避免文件同名现象
		 * 3校验文件的扩展名：只能是jpg,校验图片的尺寸
		 * 4指定图片的保存路径
		 * 5把图片的路径设置给Goods对象
		 */
		// 获取文件名
		FileItem fileItem = fileItemList2.get(0);//获取大图
		String filename = fileItem.getName();
		// 截取文件名，因为部分浏览器上传的绝对路径
//		int index = filename.lastIndexOf("\\");
		int index = filename.lastIndexOf("\\");
		if(index != -1) {
			filename = filename.substring(index + 1);
		}
		// 给文件名添加uuid前缀，避免文件同名现象
		filename = CommonUtils.uuid() + "_" + filename;
		// 校验文件名称的扩展名
		if(!filename.toLowerCase().endsWith(".jpg")) {
			error("上传的图片扩展名必须是JPG", request, response);
			return;
		}
		// 校验图片的尺寸
		// 保存上传的图片，把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO
		String savepath = this.getServletContext().getRealPath("/goods_img");//图片真实路径
		File destFile = new File(savepath, filename);// 创建目标文件
		try {                                       //保存文件
			fileItem.write(destFile);//它会把临时文件重定向到指定的路径，再删除临时文件
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		// 校验尺寸
		ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());//使用文件路径创建ImageIcon
		Image image = icon.getImage();//通过ImageIcon得到Image对象
		if(image.getWidth(null) > 350 || image.getHeight(null) > 350)//获取宽高来进行校验
		{
			error("您上传的的小图，它的图片尺寸超出了350*350！", request, response);
			destFile.delete();//删除图片
			return;
		}
		goods.setImage_w("goods_img/" + filename);//把大图----图片的路径设置给goods对象
		
		fileItem = fileItemList2.get(1);//获取小图
		filename = fileItem.getName();
		index = filename.lastIndexOf("\\");
		if(index != -1) {
			filename = filename.substring(index + 1);
		}
		// 给文件名添加uuid前缀，避免文件同名现象
		filename = CommonUtils.uuid() + "_" + filename;
		// 校验文件名称的扩展名
		if(!filename.toLowerCase().endsWith(".jpg")) {
			error("上传的图片扩展名必须是JPG", request, response);
			return;
		}
		//保存图片：
		savepath = this.getServletContext().getRealPath("/goods_img");//获取真实路径
		destFile = new File(savepath, filename);//创建目标文件
		try {                                   //保存文件
			fileItem.write(destFile);//它会把临时文件重定向到指定的路径，再删除临时文件
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// 校验尺寸
		icon = new ImageIcon(destFile.getAbsolutePath());//使用文件路径创建ImageIcon
		image = icon.getImage();//通过ImageIcon得到Image对象
		// 获取宽高来进行校验
		if(image.getWidth(null) > 200 || image.getHeight(null) > 200) {
			error("您上传的的小图，它的图片尺寸超出了200*200！", request, response);
			destFile.delete();//删除图片
			return;
		}
		goods.setImage_b("goods_img/" + filename);// 把小图----图片的路径设置给goods对象
		
		// 调用service完成保存
		goods.setGid(CommonUtils.uuid());
		
		//添加商品
		StoreGoodsService storeGoodsService = new StoreGoodsService();
		storeGoodsService.add(goods);
		
		// 保存成功信息转发到msg.jsp
		request.setAttribute("msg", "添加商品成功！");
		request.getRequestDispatcher("/storejsps/store/storeCategroy/msg.jsp").forward(request, response);
	}
	
	 public void doGet(HttpServletRequest request, HttpServletResponse response)
	          throws ServletException, IOException {
	      doPost(request, response);
   }
	
	/*
	 * 保存错误信息，转发到/storejsps/store/storeCategroy/add.jsp
	 */
	private void error(String msg, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/storejsps/store/storeCategroy/add.jsp").
				forward(request, response);
	}
}
