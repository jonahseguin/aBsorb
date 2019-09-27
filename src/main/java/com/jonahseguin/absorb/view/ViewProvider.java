/*
 * Copyright (c) 2019 Jonah Seguin.  All rights reserved.  You may not modify, decompile, distribute or use any code/text contained in this document(plugin) without explicit signed permission from Jonah Seguin.
 */

package com.jonahseguin.absorb.view;

import org.bukkit.entity.Player;

import java.util.List;

public interface ViewProvider {

    String getTitle(Player player);

    List<String> getLines(Player player);

}
