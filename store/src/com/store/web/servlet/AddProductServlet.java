package com.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.store.domain.Category;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.utils.BeanFactory;
import com.store.utils.UUIDUtils;
import com.store.utils.UploadUtils;

import sun.nio.ch.IOUtil;

/**
 * 
 */
public class AddProductServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			//创建map,接受前台数据
			HashMap<String, Object> map = new HashMap<>();
			//创建磁盘文件项
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//创建核心上传对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解析request
			List<FileItem> list = upload.parseRequest(request);
			//遍历集合
			for (FileItem fi : list) {
				//判断是否是普通的上传组件
				if(fi.isFormField()){
					//普通上传组件
					map.put(fi.getFieldName(),fi.getString("utf-8"));
				}else{
					//文件上传组件
					//获取文件名称
					String name = fi.getName();
					
					//获取文件的真实名称    xxxx.xx
					String realName = UploadUtils.getRealName(name);
					//获取文件的随机名称
					String uuidName = UploadUtils.getUUIDName(realName);
					
					//获取文件的存放路径
					String path = this.getServletContext().getRealPath("/products/1");
					
					//获取文件流
					InputStream is = fi.getInputStream();
					//保存图片
					FileOutputStream os = new FileOutputStream(new File(path, uuidName));
					
					IOUtils.copy(is, os);
					os.close();
					is.close();
					
					//删除临时文件
					fi.delete();
					
					//在map中设置文件的路径
					map.put(fi.getFieldName(), "products/1/"+uuidName);
					
				}
				
			}
			
			
			//封装product
			Product product = new Product();
			BeanUtils.populate(product, map);
			
			//设置商品id
			product.setPid(UUIDUtils.getId());
			
			//设置商品时间new java.sql.Date(new Date().getTime())
			product.setPdate(new java.sql.Date(new Date().getTime()));
			
			//设置cid
			Category c = new Category();
			c.setCid((String)map.get("cid"));
			
			//设置类别Category
			product.setCategory(c);
			
			
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.add(product);
			
			//3.页面重定向
			response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "商品添加失败");
			request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
			return;
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request,  response);
	}

		
}
