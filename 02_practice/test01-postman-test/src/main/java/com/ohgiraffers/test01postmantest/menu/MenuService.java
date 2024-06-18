package com.ohgiraffers.test01postmantest.menu;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public List<MenuDTO> selectAllMenu() {
        List<Menu> menuList = menuRepository.findAll();
        MenuDTO menuDTO = new MenuDTO();

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());
    }

    public MenuDTO selectOneMenu(int menuCode) {
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(menu, MenuDTO.class);
    }

    public void registryMenu(MenuDTO menuDTO) {
        System.out.println("menuDTO = " + menuDTO);
        Menu menu = new Menu(menuDTO.getMenuCode(),menuDTO.getMenuName(),menuDTO.getMenuPrice(),menuDTO.getCategoryCode(),menuDTO.getOrderableStatus());
        System.out.println("menu = " + menu);
        menuRepository.save(menu);
    }

    @Transactional
    public void modifyMenu(MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(menuDTO.getMenuCode()).orElseThrow(IllegalArgumentException::new);

        menu = menu.toBuilder().menuName(menuDTO.getMenuName()).build();
        menuRepository.save(menu);

    }
}
