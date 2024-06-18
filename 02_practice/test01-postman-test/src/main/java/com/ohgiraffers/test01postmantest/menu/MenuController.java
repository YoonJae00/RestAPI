package com.ohgiraffers.test01postmantest.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu")
    public ResponseEntity<ResponseInfo> selectAllMenu() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        List<MenuDTO> menuList = menuService.selectAllMenu();

        Map<String,Object> menuMap = new HashMap<>();
        menuMap.put("menuList",menuList);
        ResponseInfo responseInfo = new ResponseInfo("Good","조회 성공",menuMap);
//        return ResponseEntity<new ResponseInfo("Good","조회 성공",menuMap)>;

        return new ResponseEntity<>(responseInfo,headers, HttpStatus.OK);
    }

    @GetMapping("/menu/{menuCode}")
    public ResponseEntity<MenuDTO> selectMenu(@PathVariable("menuCode") int menuCode) {

        MenuDTO menu = menuService.selectOneMenu(menuCode);

        return ResponseEntity.ok(menu);
    }

    @PostMapping("/menu")
    public ResponseEntity<?> insertMenu(@RequestBody MenuDTO menuDTO) {

        System.out.println("menuDTO = " + menuDTO);
        menuService.registryMenu(menuDTO);

        return ResponseEntity.created(URI.create("/menu/"+menuDTO.getMenuCode())).build();
    }

    @PutMapping("/menu")
    public ResponseEntity<?> updateMenu(@RequestBody MenuDTO menuDTO) {

        menuService.modifyMenu(menuDTO);

        Map<String,Object> info = new HashMap<>();
        info.put("info","success");
        info.put("modifyMenu",menuDTO);
        return ResponseEntity.ok().body(info);
    }
}
