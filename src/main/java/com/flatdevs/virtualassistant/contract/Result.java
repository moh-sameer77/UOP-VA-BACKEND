package com.flatdevs.virtualassistant.contract;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Result
{
    private boolean success;
    private Object message;
}
