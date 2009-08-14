/*
 * Copyright (c) JForum Team. All rights reserved.
 *
 * The software in this package is published under the terms of the LGPL
 * license a copy of which has been included with this distribution in the
 * license.txt file.
 *
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.plugins.tagging;

import net.jforum.actions.helpers.Actions;
import net.jforum.actions.interceptors.ActionSecurityInterceptor;
import net.jforum.core.SecurityConstraint;
import net.jforum.core.support.vraptor.ViewPropertyBag;
import net.jforum.security.AdministrationRule;
import net.jforum.services.ViewService;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;
import org.vraptor.annotations.Parameter;

/**
 * @author Bill
 *
 */
@Component("adminTag")
@InterceptedBy(ActionSecurityInterceptor.class)
@SecurityConstraint(value = AdministrationRule.class, displayLogin = true)
public class TagAdminActions {
	private ViewService viewService;
	private ViewPropertyBag propertyBag;
	private TagRepository repository;
	
	public TagAdminActions(ViewPropertyBag propertyBag,
			ViewService viewService,TagRepository repository) {
		this.propertyBag = propertyBag;
		this.repository = repository;
		this.viewService = viewService;
	}
	
	public void delete(@Parameter(key = "tags") String... tags) {
		if (tags != null) {
			for (String tag : tags) {
				this.repository.remove(tag);
			}
		}

		this.viewService.redirectToAction(Actions.LIST);
	}

	public void list() {
		this.propertyBag.put("tags", this.repository.getAll());
	}

	public void add() {
	}

	public void edit(@Parameter(key = "name") String tag) {
		this.propertyBag.put("name", tag);
		this.viewService.renderView(Actions.ADD);
	}

	public void editsave(@Parameter(key = "oldTag") String oldTag,@Parameter(key = "newTag") String newTag) {
		this.repository.update(oldTag,newTag);
		this.viewService.redirectToAction(Actions.LIST);
	}
}
