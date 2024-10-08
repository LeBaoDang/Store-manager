package com.microservice.admin.service.impl;

import com.microservice.admin.dto.reponse.PageResponse;
import com.microservice.admin.dto.reponse.PermissionRequest;
import com.microservice.admin.dto.reponse.RolesRequest;
import com.microservice.admin.entitys.Roles;
import com.microservice.admin.mapper.mapstruct.RolesMapper;
import com.microservice.admin.repository.PermissionRepo;
import com.microservice.admin.repository.RolesRepo;
import com.microservice.admin.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class RolesServiceImpl implements RolesService {
	
	@Autowired
    RolesRepo rolesRepo;
	@Autowired
    RolesMapper rolesMapper;
	@Autowired
    PermissionRepo permissionRepo;
	
	@Override
	public Roles findByDescription(String description) {
		return rolesRepo.findByDescription(description);
	}

	@Override
	public Object create(RolesRequest rolesRequest) {
		Roles roles = rolesMapper.toEntity(rolesRequest);
		for(PermissionRequest s : rolesRequest.getPermission()){
			var permission = permissionRepo.findAllById(Collections.singleton(s.getName()));
			roles.setPermission(new HashSet<>(permission));
			roles = rolesRepo.save(roles);
		}
		return rolesMapper.toDto(roles);
	}


	@Override
	public Object find(Integer page, Integer limit) {
		Pageable pageable = PageRequest.of(page,limit);
		Page<Roles> pages = rolesRepo.findAll(pageable);
		if(!pages.isEmpty()){
			return PageResponse
					.builder()
					.total(pages.getTotalElements())
					.page(pages.getNumber())
					.limit(pages.getSize())
					.items(Collections.singletonList(pages.getContent().stream()
							.map(rolesMapper::toDto).toList()))
					.build();
		}
		return null;
	}

	@Override
	public Object delete(String name) {
		rolesRepo.deleteById(name);
		return "Xóa thành công";
	}

}
