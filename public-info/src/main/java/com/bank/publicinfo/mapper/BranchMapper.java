package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.Branch;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BranchMapper {

    @Named("toDto")
    BranchDto toDto(Branch branch);

    Branch toEntity(BranchDto branchDto);

    @IterableMapping(qualifiedByName = "toDto")
    List<BranchDto> toDtoList(List<Branch> branches);
}
