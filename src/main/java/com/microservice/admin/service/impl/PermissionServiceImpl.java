package com.microservice.admin.service.impl;

import com.microservice.admin.dto.reponse.PageResponse;
import com.microservice.admin.dto.reponse.PermissionRequest;
import com.microservice.admin.entitys.Permission;
import com.microservice.admin.mapper.mapstruct.PermissionMapper;
import com.microservice.admin.repository.PermissionRepo;
import com.microservice.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionRepo permissionRepo;
    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public Object create(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toEntity(permissionRequest);
        return permissionMapper.toDto(permissionRepo.saveAndFlush(permission));
    }

    @Override
    public Object find(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page,limit);
        Page<Permission> pages = permissionRepo.findAll(pageable);
        if(!pages.isEmpty()){
            return PageResponse
                    .builder()
                    .total(pages.getTotalElements())
                    .page(pages.getNumber())
                    .limit(pages.getSize())
                    .items(Collections.singletonList(pages.getContent().stream()
                            .map(permissionMapper::toDto).toList()))
                    .build();
        }
        return null;
    }

    @Override
    public Object delete(String name) {
        permissionRepo.deleteById(name);
        return "Xóa thành công";
    }
}
