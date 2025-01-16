package com.daengdaeng_eodiga.project.user.dto;

import java.util.List;

public record UserAndPetInfo(int userId, String userName, List<PetInfo> pets) {
}
