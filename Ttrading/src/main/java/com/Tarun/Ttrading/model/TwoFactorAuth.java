package com.Tarun.Ttrading.model;

import com.Tarun.Ttrading.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {

    private boolean isEnabled = false;
    private VerificationType sendTo;
}
