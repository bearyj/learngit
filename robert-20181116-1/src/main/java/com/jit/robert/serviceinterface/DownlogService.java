package com.jit.robert.serviceinterface;



import com.jit.robert.domain.Downlog;

import java.text.ParseException;
import java.util.List;

public interface DownlogService {
    Downlog addDownlog(String downlogname) throws ParseException;
    Boolean deleteDownlogBatch(String ids);
    List<Downlog> getAllDownlog();
}
