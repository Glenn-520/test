package com.seatig.controller;

import com.seatig.common.Result;
import com.seatig.common.ResultGenerator;
import com.seatig.domain.*;
import com.seatig.service.ContentService;
import com.seatig.service.ICompanyService;
import com.seatig.utils.JSONUtils;
import com.seatig.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Controller
public class ContentController {
    @Autowired
    ContentService contentService;

    @Autowired
    ICompanyService companyService;
    @ModelAttribute(value = "city")
    public List<String> gedata(){
        return Arrays.asList("北京","绵阳","江油");
    }

      @RequestMapping(value = "/test/{abc}",method = RequestMethod.GET,produces = "text/plain;charset=UTF-8")
          public ModelAndView test(User user){
          System.out.println("username="+user.getUsername());
          System.out.println("password="+user.getPassword());

          ModelAndView mv=new ModelAndView();
            mv.setViewName("test");
              return mv;
          }



    /**
     * @Description: 根据条件查询 返回企业 or 机构信息
     * @Param: [request, response]
     * @return: com.seatig.quanaxy.common.Result
     * @Author: glenn
     * @Date: 2019/7/8
     */
    @RequestMapping(value = "/get-companys")
    public @ResponseBody
    Result getCompanys(PageDTO<Company> pageDTO, String search) {
        List<Company> list=companyService.getCompanyByStr(pageDTO.page(),search);

        return ResultGenerator.getSuccessResult(list);
    }



      @RequestMapping(value = "test",method = RequestMethod.GET,produces = "text/plain;charset=UTF-8")
          public @ResponseBody Result getElementList(HttpServletRequest request, HttpServletResponse response){
              Result result=new Result();

              contentService.test();
              return ResultGenerator.getSuccessResult();
          }










    @RequestMapping(value = "getcontent", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String getContent(HttpServletRequest request, HttpServletResponse response) {

        Result result = new Result();

        int page = 0;
        String pagenum = request.getParameter("page");
        if (pagenum != null) {
            page = Integer.parseInt(pagenum);
        }
        int num = 20;
        if (page == 0) {
            return JSONUtils.beanToJson(result);
        }
        List<Content> contents = contentService.getContent(num, page);

        result.setData(contents);
        return JSONUtils.beanToJson(result);
    }


    @RequestMapping(value = "uploadcontent", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public @ResponseBody
    String uploadContent(MultipartHttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        String imgUser = request.getParameter("img_user");
        String imgTitle = request.getParameter("img_title");
        String imgTxt = request.getParameter("img_txt");
        Content content=new Content();
        if (imgUser == null) {
            content.setImgUser(1);
        } else {
            content.setImgUser(Integer.parseInt(imgUser));
        }
        if (imgTitle == null) {
            content.setImgTitle("空");
        } else {
            content.setImgTitle(imgTitle);
        }
        if (imgTxt == null) {
            content.setImgTxt("空");
        } else {
            content.setImgTxt(imgTxt);
        }


        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            //用户上传了图片
            MultipartFile file = request.getFile(fileNames.next());
            String imgFileName = Utils.getTime() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            try {
                String img_url = Utils.get("img_url", Thread.currentThread().getContextClassLoader().getResource("/").getPath() + "properties/user.properties");
                img_url += imgFileName;
                String img_path = Utils.get("img_path", Thread.currentThread().getContextClassLoader().getResource("/").getPath() + "properties/user.properties");
                img_path+=imgFileName;
                File file_path = new File(img_path);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file_path));
                Utils.fileToDisk(bos, file.getInputStream());
                content.setImgUrl(img_url);
                contentService.setContent(content);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return JSONUtils.beanToJson(result);
        }
        return JSONUtils.beanToJson(result);
    }
}
