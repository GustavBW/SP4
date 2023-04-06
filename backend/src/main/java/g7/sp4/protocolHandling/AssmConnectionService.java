package g7.sp4.protocolHandling;

import g7.sp4.common.models.AssmStatus;

public interface AssmConnectionService {

	AssmStatus getStatus();
	Flag build();
}

