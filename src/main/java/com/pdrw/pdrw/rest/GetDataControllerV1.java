package com.pdrw.pdrw.rest;

import com.pdrw.pdrw.model.Item;
import com.pdrw.pdrw.service.GetDataService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/data")
public class GetDataControllerV1 {

    private final GetDataService getDataService;

    public GetDataControllerV1(GetDataService getDataService) {
        this.getDataService = getDataService;
    }

    @PostMapping("/set-data")
    public List<Item> setData(@RequestBody() String data) throws IOException {
        return getDataService.getData(10);
    }


}
