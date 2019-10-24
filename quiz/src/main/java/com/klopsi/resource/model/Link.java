package com.klopsi.resource.model;

import lombok.*;

import java.net.URI;

/**
 * Represents link for HATEOAS REST api. Using standard javax.ws.rs.core.Link can cause problems. Different servers
 * may use different JSONB or JAXB implementations causing using different object mappers. The javax.ws.rs.core.Link
 * can not be mapped one-to-one as it introduces much more properties than those required in HATEOAS. Writing new
 * simple class is easier than custom made mappers.
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Link {
	/**
	 * Resource URI.
	 */
	private URI href;

	/**
	 * HTTP method.
	 */
	private String method;
}
