package com.casic.bank.mapper;

import com.casic.bank.domain.InCap;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InCapMapper {

    List<InCap> selectInCap(InCap inCap);

    Integer selectCountByFileIds(List<String> fileIds);

    Integer getInCapListCount(InCap inCap);

}
